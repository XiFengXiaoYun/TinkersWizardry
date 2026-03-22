package com.xifeng.tinkers_wizardry.client.book;

import c4.conarm.lib.modifiers.ArmorModifierTrait;
import com.xifeng.tinkers_wizardry.conarm.ConarmModifiers;
import slimeknights.mantle.client.book.data.BookData;
import slimeknights.mantle.client.book.data.PageData;
import slimeknights.mantle.client.book.data.SectionData;
import slimeknights.mantle.client.book.repository.BookRepository;
import slimeknights.tconstruct.library.book.content.ContentListing;
import slimeknights.tconstruct.library.book.sectiontransformer.SectionTransformer;

public class ArmorBookTransformerModifiers extends SectionTransformer {
    private final BookRepository source;
    public ArmorBookTransformerModifiers(BookRepository source) {
        super("modifiers");
        this.source = source;
    }

    @Override
    public void transform(BookData book, SectionData section) {
        ContentListing listing = (ContentListing)section.pages.get(0).content;
        for (ArmorModifierTrait mod : ConarmModifiers.modifiers) {
            PageData page = new PageData();
            page.source = source;
            page.parent = section;
            page.type = "armormodifier";
            page.data = "modifiers/" + mod.identifier + ".json";
            section.pages.add(page);
            page.load();
            listing.addEntry(mod.getLocalizedName(), page);
        }
    }
}
