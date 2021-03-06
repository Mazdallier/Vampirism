package de.teamlapen.vampirism.client.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import cpw.mods.fml.client.config.DummyConfigElement;
import cpw.mods.fml.client.config.GuiConfig;
import cpw.mods.fml.client.config.GuiConfigEntries;
import cpw.mods.fml.client.config.GuiConfigEntries.CategoryEntry;
import cpw.mods.fml.client.config.IConfigElement;
import de.teamlapen.vampirism.Configs;
import de.teamlapen.vampirism.util.REFERENCE;

public class ModConfigGui extends GuiConfig {

	public static class BalanceEntry extends CategoryEntry {

		@SuppressWarnings("rawtypes")
		private static List<IConfigElement> getConfigElements() {
			List<IConfigElement> list = new ArrayList<IConfigElement>();
			list.add(new DummyConfigElement.DummyCategoryElement("balance_level", "category.vampirism.balance_level", BalanceLevelEntry.class));
			list.add(new DummyConfigElement.DummyCategoryElement("balance_player_mod", "category.vampirism.balance_player_mod",
					BalancePlayerModEntry.class));
			list.addAll(new ConfigElement(Configs.config.getCategory(Configs.CATEGORY_BALANCE)).getChildElements());

			return list;

		}

		public BalanceEntry(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement prop) {
			super(owningScreen, owningEntryList, prop);
		}

		@Override
		protected GuiScreen buildChildScreen() {
			return new GuiConfig(this.owningScreen, getConfigElements(), this.owningScreen.modID, Configs.CATEGORY_BALANCE, false, false,
					REFERENCE.NAME + " Balance");
		}

	}

	public static class BalanceLevelEntry extends CategoryEntry {

		public BalanceLevelEntry(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement configElement) {
			super(owningScreen, owningEntryList, configElement);
		}

		@Override
		protected GuiScreen buildChildScreen() {
			return new GuiConfig(this.owningScreen,
					(new ConfigElement(Configs.config.getCategory(Configs.CATEGORY_BALANCE_LEVELING))).getChildElements(), this.owningScreen.modID,
					Configs.CATEGORY_BALANCE_LEVELING, false, false, REFERENCE.NAME + " Balance");
		}

	}

	public static class BalancePlayerModEntry extends CategoryEntry {

		public BalancePlayerModEntry(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement configElement) {
			super(owningScreen, owningEntryList, configElement);
		}

		@Override
		protected GuiScreen buildChildScreen() {
			return new GuiConfig(this.owningScreen,
					(new ConfigElement(Configs.config.getCategory(Configs.CATEGORY_BALANCE_PLAYER_MOD))).getChildElements(), this.owningScreen.modID,
					Configs.CATEGORY_BALANCE_PLAYER_MOD, false, false, REFERENCE.NAME + " Balance");
		}
	}

	@SuppressWarnings("rawtypes")
	private static List<IConfigElement> getConfigElements() {
		List<IConfigElement> list = new ArrayList<IConfigElement>();
		list.addAll(new ConfigElement(Configs.config.getCategory(Configs.CATEGORY_GENERAL)).getChildElements());
		list.addAll(new ConfigElement(Configs.config.getCategory(Configs.CATEGORY_VILLAGE)).getChildElements());
		list.add(new DummyConfigElement.DummyCategoryElement("balance", "category.vampirism.balance", BalanceEntry.class));
		return list;

	}

	public ModConfigGui(GuiScreen parentScreen) {
		super(parentScreen, getConfigElements(), REFERENCE.MODID, true, false, REFERENCE.NAME + " Config");
	}

}
