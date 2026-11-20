<?php
/**
 * Plugin Name: Malva Formulare
 * Description: Verschickt die Formulare auf malva-ev.de (Anmeldung Fachleute, Vormerkung Fördermitglied/Förderpartner) per E-Mail an info@malva-ev.de. Speichert nichts in der Datenbank.
 * Version: 1.1.0
 * Author: Malva
 * Requires at least: 5.0
 * Requires PHP: 7.0
 */

if ( ! defined( 'ABSPATH' ) ) {
	exit;
}

const MALVA_FORM_EMPFAENGER = 'info@malva-ev.de';

add_action( 'init', 'malva_formular_verarbeiten' );

function malva_formular_verarbeiten() {
	if ( 'POST' !== ( $_SERVER['REQUEST_METHOD'] ?? '' ) || empty( $_POST['malva_formular'] ) ) {
		return;
	}
	$formular = sanitize_key( wp_unslash( $_POST['malva_formular'] ) );
	$arten = array(
		'fachleute'  => array( 'param' => 'anmeldung', 'anker' => '#anmeldung', 'seite' => '/fachleute/' ),
		'vormerkung' => array( 'param' => 'vormerkung', 'anker' => '', 'seite' => '/spenden/' ),
	);
	if ( ! isset( $arten[ $formular ] ) ) {
		return;
	}
	$art = $arten[ $formular ];

	$zurueck = wp_get_referer() ? wp_get_referer() : home_url( $art['seite'] );
	$zurueck = remove_query_arg( array( 'anmeldung', 'vormerkung', 'art' ), strtok( $zurueck, '#' ) );
	if ( 'vormerkung' === $formular ) {
		$typ          = isset( $_POST['Art'] ) && false !== strpos( wp_unslash( $_POST['Art'] ), 'partner' ) ? 'partner' : 'mitglied';
		$zurueck      = add_query_arg( 'art', $typ, $zurueck );
		$art['anker'] = '#vormerken-' . $typ;
	}

	// Spamschutz: verstecktes Feld muss leer bleiben.
	if ( ! empty( $_POST['malva_website'] ) ) {
		wp_safe_redirect( add_query_arg( $art['param'], 'danke', $zurueck ) . $art['anker'] );
		exit;
	}

	$daten = wp_unslash( $_POST );
	$name  = isset( $daten['Name'] ) ? sanitize_text_field( $daten['Name'] ) : '';
	$mail  = isset( $daten['E-Mail'] ) ? sanitize_email( $daten['E-Mail'] ) : '';
	$ok    = ! empty( $daten['Einwilligung'] );

	if ( '' === $name || ! is_email( $mail ) || ! $ok ) {
		wp_safe_redirect( add_query_arg( $art['param'], 'fehler', $zurueck ) . $art['anker'] );
		exit;
	}

	$ignorieren = array( 'malva_formular', 'malva_website' );
	$zeilen     = array();
	foreach ( $daten as $feld => $wert ) {
		if ( in_array( $feld, $ignorieren, true ) ) {
			continue;
		}
		if ( is_array( $wert ) ) {
			$wert = implode( ', ', array_map( 'sanitize_text_field', $wert ) );
		} else {
			$wert = sanitize_textarea_field( $wert );
		}
		if ( '' === trim( $wert ) ) {
			continue;
		}
		$beschriftung = str_replace( '_', ' ', sanitize_text_field( $feld ) );
		$zeilen[]     = $beschriftung . ': ' . mb_substr( $wert, 0, 3000 );
	}

	if ( 'vormerkung' === $formular ) {
		$was     = isset( $daten['Art'] ) ? sanitize_text_field( $daten['Art'] ) : 'Fördermitglied';
		$betreff = 'Vormerkung ' . $was . ' – ' . $name;
		$text    = "Neue unverbindliche Vormerkung über malva-ev.de/spenden\n\n" . implode( "\n", $zeilen ) . "\n";
	} else {
		$betreff = 'Anmeldung Fachkolleg:in – ' . $name;
		$text    = "Neue Anmeldung über malva-ev.de/fachleute\n\n" . implode( "\n", $zeilen ) . "\n";
	}
	$kopf    = array(
		'From: Malva Website <' . MALVA_FORM_EMPFAENGER . '>',
		'Reply-To: ' . $name . ' <' . $mail . '>',
		'Content-Type: text/plain; charset=UTF-8',
	);

	$gesendet = wp_mail( MALVA_FORM_EMPFAENGER, $betreff, $text, $kopf );

	wp_safe_redirect( add_query_arg( $art['param'], $gesendet ? 'danke' : 'fehler', $zurueck ) . $art['anker'] );
	exit;
}
