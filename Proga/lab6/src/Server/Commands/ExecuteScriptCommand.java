package server.Commands;


import server.Service.ArgsForCommands;

import java.io.*;
import java.util.Scanner;


/**
 * The type Execute script command.
 */
public class ExecuteScriptCommand implements Command, TypeOfArgument {
	private ArgsForCommands argsForScript;

	@Override
	public void setArgs(Object args){
		this.argsForScript = (ArgsForCommands) args;
	}

	@Override
	public String executeCommand(){
		try(BufferedReader sc = new BufferedReader(new InputStreamReader(new FileInputStream(argsForScript.fileName)))){
			while(sc.readLine() != null){
				String line = sc.readLine().trim();
			}
		} catch (Exception e){
			e.printStackTrace();
		}



		return "Script executed successfully";
	}

	@Override
	public String getName() {
		return "execute_script";
	}
	@Override
	public boolean needsArgument() {
		return true;
	}

	@Override
	public String typeOfArgument() {
		return "String";
	}

	@Override
	public String describeCommand(){
		return " - script from file";
	}
}
