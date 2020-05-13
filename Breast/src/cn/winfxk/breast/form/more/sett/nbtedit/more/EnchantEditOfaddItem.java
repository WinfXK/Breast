package cn.winfxk.breast.form.more.sett.nbtedit.more;

import java.util.ArrayList;
import java.util.List;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;
import cn.winfxk.breast.form.FormBase;
import cn.winfxk.breast.tool.CustomForm;
import cn.winfxk.breast.tool.EnchantList;
import cn.winfxk.breast.tool.Tool;

/**
 * @Createdate 2020/05/13 11:36:05
 * @author Winfxk
 */
public class EnchantEditOfaddItem extends FormBase {
	private int Index;
	private Item item;
	private EnchantList eList;
	private List<EnchantList> list = new ArrayList<>();

	public EnchantEditOfaddItem(Player player, FormBase upForm, int Index) {
		super(player, upForm);
		this.Index = Index;
		setSon("NBTEditorOfEditItemOfEnchantEditOfaddItem");
		setK("{Player}", "{Money}", "{ItemName}", "{ItemID}", "{ItemDamage}", "{ItemPatn}");
		setD(player.getName(), myPlayer.getMoney(), itemList.getName(item), item.getId(), item.getDamage(),
				itemList.getPath(item));
		eList = ac.getEnchants();
	}

	@Override
	public boolean MakeMain() {
		if (!myPlayer.isAdmin()) {
			player.sendMessage(getString("权限不足"));
			return isBack();
		}
		CustomForm form = new CustomForm(getID(), getTitle());
		form.addLabel(getContent());
		String[] KK = { "{Player}", "{Money}", "{ItemName}", "{ItemID}", "{ItemDamage}", "{ItemPatn}", "{EnchantName}",
				"{EnchantID}" };
		for (EnchantList enchant : eList.getAll()) {
			listKey.add(getString("EnchantItem", KK,
					new Object[] { player.getName(), myPlayer.getMoney(), itemList.getName(item), item.getId(),
							item.getDamage(), itemList.getPath(item), enchant.getName(), enchant.getID() }));
			list.add(enchant);
		}
		form.addDropdown(getString("SelectItem"), listKey);
		form.addInput(getString("InputLevel"), "", getString("InputLevel"));
		form.sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		FormResponseCustom d = getCustom(data);
		Enchantment enchantment = list.get(d.getDropdownResponse(1).getElementID()).getEnchant();
		String str = d.getInputResponse(2);
		int Level = Tool.isInteger(str) ? Tool.ObjToInt(str) : enchantment.getMinLevel();
		enchantment.setLevel(Level > enchantment.getMaxLevel() ? enchantment.getMaxLevel() : Level);
		item.addEnchantment(enchantment);
		player.getInventory().setItem(Index, item);
		player.sendMessage(getString("addOK"));
		return isBack();
	}
}
