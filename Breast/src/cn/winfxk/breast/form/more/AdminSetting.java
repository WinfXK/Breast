package cn.winfxk.breast.form.more;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.winfxk.breast.form.FormBase;
import cn.winfxk.breast.form.Setting;
import cn.winfxk.breast.form.more.sett.EnchantList;
import cn.winfxk.breast.form.more.sett.ItemList;
import cn.winfxk.breast.form.more.sett.NBTEditor;
import cn.winfxk.breast.form.more.sett.NBTList;
import cn.winfxk.breast.tool.SimpleForm;

/**
 * 更多设置的页面
 * 
 * @Createdate 2020/05/12 11:06:31
 * @author Winfxk
 */
public class AdminSetting extends FormBase {

	public AdminSetting(Player player, FormBase upForm) {
		super(player, upForm);
	}

	@Override
	public boolean MakeMain() {
		if (!myPlayer.isAdmin()) {
			player.sendMessage(msg.getMessage("权限不足"));
			return upForm == null ? true : setForm(upForm).make();
		}
		SimpleForm form = new SimpleForm(getID(), getTitle(), getContent());
		form.addButton(getString("ItemList"));
		form.addButton(getString("EnchantList"));
		form.addButton(getString("NBTList"));
		form.addButton(getString("Setting"));
		form.addButton(getString("NBTEditor"));
		form.addButton(getBack());
		form.sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		switch (getSimple(data).getClickedButtonId()) {
		case 0:
			setForm(new ItemList(player, this));
			break;
		case 1:
			setForm(new EnchantList(player, this));
			break;
		case 2:
			setForm(new NBTList(player, this));
			break;
		case 3:
			setForm(new Setting(player, this));
			break;
		case 4:
			setForm(new NBTEditor(player, this));
			break;
		default:
			return upForm == null ? true : setForm(upForm).make();
		}
		return make();
	}
}
