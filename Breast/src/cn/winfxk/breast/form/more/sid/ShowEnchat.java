package cn.winfxk.breast.form.more.sid;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;
import cn.winfxk.breast.form.FormBase;
import cn.winfxk.breast.tool.SimpleForm;

/**
 * 显示物品拥有的附魔效果
 * 
 * @Createdate 2020/05/13 15:04:58
 * @author Winfxk
 */
public class ShowEnchat extends FormBase {
	private Item item;

	public ShowEnchat(Player player, FormBase upForm, Item item) {
		super(player, upForm);
		this.item = item;
		setSon("ShowItemDataOfShowEnchat");
		setK("{Player}", "{Money}", "{ItemName}", "{ItemID}", "{ItemDamage}", "{ItemPatn}", "{ItemCount}");
		setD(player.getName(), myPlayer.getMoney(), itemList.getName(item), item.getId(), item.getDamage(),
				itemList.getPath(item), item.getCount());
	}

	@Override
	public boolean MakeMain() {
		if (item.getEnchantments().length <= 0) {
			player.sendMessage(getString("notEnchant"));
			return isBack();
		}
		String string = "";
		String[] kKStrings = { "{Player}", "{Money}", "{ItemName}", "{ItemID}", "{ItemDamage}", "{ItemPatn}",
				"{ItemCount}", "{EnchantName}", "{EnchantID}", "{EnchantLevel}" };
		SimpleForm form = new SimpleForm(getID(), getTitle(), getContent());
		for (Enchantment enchantment : item.getEnchantments()) {
			string += "\n" + getString("EnchatItem", kKStrings,
					new Object[] { player.getName(), myPlayer.getMoney(), itemList.getName(item), item.getId(),
							item.getDamage(), itemList.getPath(item), item.getCount(),
							ac.getEnchants().getName(enchantment), enchantment.getId(), enchantment.getLevel() });
		}
		form.setContent(form.getContent() + string);
		form.addButton(getBack());
		form.sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		return isBack();
	}
}
