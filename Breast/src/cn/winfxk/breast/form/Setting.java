package cn.winfxk.breast.form;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.utils.Config;
import cn.winfxk.breast.money.MyEconomy;
import cn.winfxk.breast.tool.CustomForm;
import cn.winfxk.breast.tool.Tool;

/**
 * 插件设置页面
 * 
 * @Createdate 2020/05/12 11:34:32
 * @author Winfxk
 */
public class Setting extends FormBase {
	private Config config;

	public Setting(Player player, FormBase upForm) {
		super(player, upForm);
		config = ac.getConfig();
	}

	@Override
	public boolean MakeMain() {
		if (!myPlayer.isAdmin()) {
			player.sendMessage(msg.getMessage("权限不足", player));
			return false;
		}
		CustomForm form = new CustomForm(getID(), getTitle());
		form.addLabel(getContent());
		form.addInput(getString("MainEconomyName"), config.getString("EconomyAPI货币名称"), getString("MainEconomyName"));
		form.addDropdown(getString("Economy"), ac.getEconomyManage().getEconomy(),
				ac.getEconomyManage().getEconomy().indexOf(ac.getEconomy().getEconomyName()));
		form.addToggle(getString("Update"), config.getBoolean("检查更新"));
		form.addInput(getString("ServiceCharge"), config.get("上架手续费"), getString("ServiceCharge"));
		form.addDropdown(getString("ServiceChargeEconomy"), ac.getEconomyManage().getEconomy(),
				ac.getEconomyManage().getEconomy().indexOf(config.getString("手续费货币")));
		form.addSlider(getString("ShowItemCount"), 1, ac.getItemListConfig().getAll().size(), 1,
				config.getInt("物品列表显示数量"));
		form.sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		FormResponseCustom d = getCustom(data);
		String EconomyName = d.getInputResponse(1);
		if (EconomyName == null || EconomyName.isEmpty()) {
			player.sendMessage(getString("notInputMainEconomyName"));
			return MakeMain();
		}
		MyEconomy economy = ac.getEconomyManage().getEconomy(d.getDropdownResponse(2).getElementContent());
		boolean Update = d.getToggleResponse(3);
		String string = d.getInputResponse(4);
		if (string == null || string.isEmpty() || !Tool.isInteger(string) || Tool.objToDouble(string) < 0) {
			player.sendMessage(getString("ServiceChargeIllegal"));
			return MakeMain();
		}
		MyEconomy updatEconomy = ac.getEconomyManage().getEconomy(d.getDropdownResponse(5).getElementContent());
		ac.setEconomy(EconomyName);
		int ItemListShowList = Tool.ObjToInt(d.getSliderResponse(6));
		config.set("EconomyAPI货币名称", EconomyName);
		config.set("默认货币", economy.getEconomyName());
		config.set("检查更新", Update);
		config.set("上架手续费", Tool.objToDouble(string));
		config.set("手续费货币", updatEconomy.getEconomyName());
		config.set("物品列表显示数量", ItemListShowList);
		player.sendMessage(getString("SettingOK"));
		return config.save() && isBack();
	}
}
