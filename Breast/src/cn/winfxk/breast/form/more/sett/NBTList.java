package cn.winfxk.breast.form.more.sett;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.nukkit.utils.Config;
import cn.winfxk.breast.form.FormBase;
import cn.winfxk.breast.tool.SimpleForm;

/**
 * 显示存储的NBT列表
 * 
 * @Createdate 2020/05/12 11:32:32
 * @author Winfxk
 */
public class NBTList extends FormBase {
	private Config config;

	public NBTList(Player player, FormBase upForm) {
		super(player, upForm);
		config = ac.getNBTConfig();
	}

	@Override
	public boolean MakeMain() {
		SimpleForm form = new SimpleForm(getID(), getTitle(), getContent());
		for (String string : config.getKeys()) {
			if (config.get(string) == null)
				continue;
			listKey.add(string);
			form.addButton(string);
		}
		form.addButton(getBack());
		form.sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		int ID = getSimple(data).getClickedButtonId();
		if (!myPlayer.isAdmin() || ID >= listKey.size())
			return isBack();
		config.remove(listKey.get(ID));
		player.sendMessage(getString("delOK"));
		return config.save() && isBack();
	}
}
