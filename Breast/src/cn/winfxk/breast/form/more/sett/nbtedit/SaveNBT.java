package cn.winfxk.breast.form.more.sett.nbtedit;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.nukkit.utils.Config;
import cn.winfxk.breast.form.FormBase;
import cn.winfxk.breast.tool.CustomForm;

/**
 * 保存物品NBT
 * 
 * @Createdate 2020/05/13 11:23:25
 * @author Winfxk
 */
public class SaveNBT extends FormBase {
	private byte[] nbt;

	public SaveNBT(Player player, FormBase upForm, byte[] nbt) {
		super(player, upForm);
		this.nbt = nbt;
		setSon("NBTEditorOfEditItemOfSaveNBT");
	}

	@Override
	public boolean MakeMain() {
		if (!myPlayer.isAdmin()) {
			player.sendMessage(getString("权限不足"));
			return isBack();
		}
		CustomForm form = new CustomForm(getID(), getTitle());
		form.addLabel(getContent());
		form.addInput(getString("InputName"), " ", getString("InputName"));
		form.sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		String string = getCustom(data).getInputResponse(1);
		if (string == null || string.isEmpty()) {
			player.sendMessage(getString("notInputName"));
			return isBack();
		}
		Config config = ac.getNBTConfig();
		if (config.getAll().containsKey(string)) {
			player.sendMessage(getString("NameIsExist"));
			return isBack();
		}
		config.set(string, nbt);
		player.sendMessage(getString("SaveOK"));
		return config.save() && isBack();
	}
}
