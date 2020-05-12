package cn.winfxk.breast.form.more.sett;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.winfxk.breast.form.FormBase;

/**
 * 物品编辑器
 * 
 * @Createdate 2020/05/12 11:34:49
 * @author Winfxk
 */
public class NBTEditor extends FormBase {

	public NBTEditor(Player player, FormBase upForm) {
		super(player, upForm);
	}

	@Override
	public boolean MakeMain() {
		return false;
	}

	@Override
	public boolean disMain(FormResponse data) {
		return false;
	}

}
