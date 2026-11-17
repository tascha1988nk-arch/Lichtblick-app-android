export default function (eleventyConfig) {
  eleventyConfig.addPassthroughCopy({ "src/assets": "assets" });
  eleventyConfig.addPassthroughCopy({ "src/favicon.svg": "favicon.svg" });

  // Platzhalter erkennen: alles, was mit „[“ beginnt.
  eleventyConfig.addFilter("isPlaceholder", (v) => typeof v === "string" && v.trim().startsWith("["));

  // Nach jedem Build: offene Platzhalter zählen, damit nichts vergessen wird.
  eleventyConfig.on("eleventy.after", async ({ results }) => {
    const offen = results
      .map((r) => ({ url: r.url, n: (r.content.match(/class="placeholder"/g) || []).length }))
      .filter((r) => r.n > 0);
    if (offen.length) {
      console.log("\n[Malva] Noch offene Platzhalter:");
      offen.forEach((r) => console.log(`  ${r.url}: ${r.n}`));
      console.log("");
    }
  });

  return {
    dir: { input: "src", includes: "_includes", data: "_data", output: "_site" },
    templateFormats: ["njk", "md"],
    htmlTemplateEngine: "njk",
    markdownTemplateEngine: "njk",
  };
}
