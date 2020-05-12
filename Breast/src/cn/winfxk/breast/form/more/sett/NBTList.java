package cn.winfxk.breast.form.more.sett;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.winfxk.breast.form.FormBase;

/**
 * 显示存储的NBT列表
 * 
 * @Createdate 2020/05/12 11:32:32
 * @author Winfxk
 */
public class NBTList extends FormBase {

	public NBTList(Player player, FormBase upForm) {
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
