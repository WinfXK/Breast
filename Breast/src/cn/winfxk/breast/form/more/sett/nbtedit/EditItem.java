package cn.winfxk.breast.form.more.sett.nbtedit;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.nukkit.item.Item;
import cn.winfxk.breast.form.FormBase;
import cn.winfxk.breast.tool.SimpleForm;

/**
 * 编辑物品界面
 * 
 * @Createdate 2020/05/13 11:10:57
 * @author Winfxk
 */
public class EditItem extends FormBase {
	private Item item;
	private int Index;

	/**
	 * 编辑物品
	 * 
	 * @param player 要编辑物品的玩家对象
	 * @param upForm 上一页
	 * @param Index  要编辑的物品在玩家背包的位置
	 */
	public EditItem(Player player, FormBase upForm, int Index) {
		super(player, upForm);
		this.Index = Index;
		item = player.getInventory().getContents().get(Index);
		setSon("NBTEditorOfEditItem");
		setK("{Player}", "{Money}", "{ItemName}", "{ItemID}", "{ItemDamage}", "{ItemPatn}");
		setD(player.getName(), myPlayer.getMoney(), itemList.getName(item), item.getId(), item.getDamage(),
				itemList.getPath(item));
	}

	@Override
	public boolean MakeMain() {
		if (!myPlayer.isAdmin()) {
			player.sendMessage(getString("权限不足"));
			return isBack();
		}
		SimpleForm form = new SimpleForm(getID(), getTitle(), getContent());
		form.addButton(getString("Enchant"));
		listKey.add("en");
		form.addButton(getString("Name"));
		listKey.add("na");
		form.addButton(getString("Lore"));
		listKey.add("lo");
		form.addButton(getString("Damage"));
		listKey.add("da");
		form.addButton(getString("Save"));
		listKey.add("sa");
		form.addButton(getString("Import"));
		listKey.add("im");
		form.addButton(getString("setCount"));
		listKey.add("sc");
		form.addButton(getBack());
		listKey.add("bk");
		form.sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		switch (listKey.get(getSimple(data).getClickedButtonId())) {
		case "en":
			setForm(new EnchantEdit(player, this, Index));
			break;
		case "na":
			setForm(new NameEdit(player, this, Index));
			break;
		case "lo":
			setForm(new LoreEdit(player, this, Index));
			break;
		case "da":
			setForm(new DamageEdit(player, this, Index));
			break;
		case "sa":
			setForm(new SaveNBT(player, this, item.getCompoundTag()));
			break;
		case "sc":
			setForm(new setCount(player, this, Index));
			break;
		case "im":
			setForm(new ImportNBT(player, this, Index));
			break;
		default:
			return isBack();
		}
		return make();
	}
}
