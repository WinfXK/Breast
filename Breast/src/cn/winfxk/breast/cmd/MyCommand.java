package cn.winfxk.breast.cmd;

import java.io.File;
import java.io.IOException;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.Utils;
import cn.winfxk.breast.Activate;
import cn.winfxk.breast.Message;
import cn.winfxk.breast.MyPlayer;
import cn.winfxk.breast.form.MainForm;
import cn.winfxk.breast.form.more.SelectItem;
import cn.winfxk.breast.tool.Tool;

/**
 * 命令处理器
 * 
 * @Createdate 2020/05/13 18:38:14
 * @author Winfxk
 */
public class MyCommand extends Command {
	private Activate ac;
	private Message msg;

	public MyCommand(Activate ac) {
		super(ac.getPluginBase().getName().toLowerCase(), ac.getPluginBase().getName().toLowerCase() + " Command",
				"/" + ac.getPluginBase().getName().toLowerCase(), ac.getCommand());
		this.ac = ac;
		msg = ac.getMessage();
		commandParameters.clear();
		commandParameters.put(msg.getSun("Command", "Parameters", "help"),
				new CommandParameter[] { new CommandParameter(msg.getSun("Command", "Parameters", "help"), false,
						new String[] { "help", "帮助" }) });
		commandParameters.put(msg.getSun("Command", "Parameters", "lang"),
				new CommandParameter[] { new CommandParameter(msg.getSun("Command", "Parameters", "lang"), false,
						new String[] { "lang", "语言" }) });
		commandParameters.put(msg.getSun("Command", "Parameters", "langs"),
				new CommandParameter[] { new CommandParameter(msg.getSun("Command", "Parameters", "langs"), false,
						new String[] { "langs", "语言列表" }) });
		commandParameters.put(msg.getSun("Command", "Parameters", "up"),
				new CommandParameter[] { new CommandParameter(msg.getSun("Command", "Parameters", "up"), false,
						new String[] { "up", "上架物品" }) });
		commandParameters.put(msg.getSun("Command", "Parameters", "main"),
				new CommandParameter[] { new CommandParameter(msg.getSun("Command", "Parameters", "main"), false,
						new String[] { "main", "主页" }) });
		commandParameters.put(msg.getSun("Command", "Parameters", "deal"),
				new CommandParameter[] { new CommandParameter(msg.getSun("Command", "Parameters", "deal"), false,
						new String[] { "deal", "交易" }) });
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		if (!sender.hasPermission("Breast.Command.main")) {
			sender.sendMessage(msg.getMessage("权限不足"));
			return true;
		}
		MyPlayer myPlayer = ac.getPlayers(sender.getName());
		if (args == null || args.length <= 0)
			if (!sender.isPlayer())
				args = new String[] { "help" };
			else {
				myPlayer.form = new MainForm(myPlayer.getPlayer(), null);
				return myPlayer.form.MakeMain();
			}
		File file;
		File[] files;
		String[] KK;
		String string;
		int ID;
		switch (args[0].toLowerCase()) {
		case "deal":
		case "交易":
			if (!sender.isPlayer()) {
				sender.sendMessage(msg.getMessage("NotPlayer"));
				return true;
			}
			myPlayer.form = new cn.winfxk.breast.form.t.SelectItem(myPlayer.getPlayer(), null);
			myPlayer.form.MakeMain();
			break;
		case "lang":
		case "lag":
		case "语言":
			file = new File(ac.getPluginBase().getDataFolder(), Activate.LanguageDirName);
			files = file.listFiles((a, b) -> new File(a, b).isFile());
			if (args.length < 2) {
				sender.sendMessage(msg.getSun("Command", "setLanguage", "notInputID", myPlayer));
				break;
			}
			string = args[1];
			if (string == null || string.isEmpty()) {
				sender.sendMessage(msg.getSun("Command", "setLanguage", "notInputID", myPlayer));
				break;
			}
			if (Tool.isInteger(string)) {
				ID = Tool.ObjToInt(string);
				if (ID == 0) {
					try {
						Utils.writeFile(Message.getFile(), Utils
								.readFile(getClass().getResourceAsStream("/resources/" + Activate.MessageFileName)));
						msg.reload();
						sender.sendMessage(msg.getSun("Command", "setLanguage", "WriteSucceed", myPlayer));
					} catch (IOException e) {
						e.printStackTrace();
						sender.sendMessage(msg.getSun("Command", "setLanguage", "WriteError", myPlayer));
					}
					break;
				}
				ID--;
				if (ID < 0 || ID >= files.length || files[ID] == null || !files[ID].exists()) {
					sender.sendMessage(msg.getSun("Command", "setLanguage", "notID", myPlayer));
					break;
				}
				try {
					Utils.writeFile(Message.getFile(), Utils.readFile(files[ID]));
					msg.reload();
					sender.sendMessage(msg.getSun("Command", "setLanguage", "WriteSucceed", myPlayer));
				} catch (IOException e) {
					e.printStackTrace();
					sender.sendMessage(msg.getSun("Command", "setLanguage", "WriteError", myPlayer));
				}
				break;
			}
			sender.sendMessage(msg.getSun("Command", "setLanguage", "notInputID", myPlayer));
			break;
		case "lags":
		case "langs":
		case "语言列表":
			KK = new String[] { "{Player}", "{Money}", "{LanguageID}", "{LanguageName}" };
			file = new File(ac.getPluginBase().getDataFolder(), Activate.LanguageDirName);
			files = file.listFiles((a, b) -> new File(a, b).isFile());
			sender.sendMessage(msg.getSun("Command", "Languages", "LanguageItem", KK,
					new Object[] { sender.getName(), myPlayer == null ? 0 : myPlayer.getMoney(), 0, "Default" }));
			Config config;
			for (int i = 0; i < files.length; i++) {
				config = new Config(files[i], Config.YAML);
				sender.sendMessage(msg.getSun("Command", "Languages", "LanguageItem", KK, new Object[] {
						sender.getName(), myPlayer == null ? 0 : myPlayer.getMoney(), i + 1, config.get("lang") }));
			}
			sender.sendMessage(msg.getSun("Command", "Languages", "SelectLanguage", myPlayer));
			break;
		case "up":
		case "上架物品":
			if (!sender.isPlayer()) {
				sender.sendMessage(msg.getMessage("NotPlayer"));
				return true;
			}
			myPlayer.form = new SelectItem(myPlayer.getPlayer(), null);
			break;
		case "main":
		case "m":
		case "主页":
			if (!sender.isPlayer()) {
				sender.sendMessage(msg.getMessage("NotPlayer"));
				return true;
			}
			myPlayer.form = new MainForm(myPlayer.getPlayer(), null);
			return myPlayer.form.MakeMain();
		case "help":
		case "h":
		case "帮助":
		default:
			sender.sendMessage(Tool.getCommandHelp(this));
			break;
		}
		return true;
	}
}
