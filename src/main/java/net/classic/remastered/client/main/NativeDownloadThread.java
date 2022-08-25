package net.classic.remastered.client.main;

import java.io.File;

public class NativeDownloadThread extends Thread {
	
	private File dir;
	private Minecraft minecraft;
	public boolean running = false;

	private boolean finished = false;
	private MinecraftOS minecraftOS;
	public NativeDownloadThread(File minecraftFolder, Minecraft minecraft) {
		this.minecraft = minecraft;

		this.setName("Resource download thread");
		this.setDaemon(true);

		dir = new File(minecraftFolder, "natives/" + minecraftOS);

		if(!dir.exists() && !dir.mkdirs())
		{
			throw new RuntimeException("The working directory could not be created: " + dir);
		}	
	}
    // Work on progress
    @Override
	public void run() {
		
	}
	
	public boolean isFinished()
	{
		return finished;
	}


}
