package info.omgwtfhax.bukkitplugins.core;

public class PermissionNode {
	private String myNode="omgwtfhax";
	
	public PermissionNode(String node)
	{
		this.setMyNode(node);
	}

	public String getMyNode() 
	{
		return myNode;
	}

	public void setMyNode(String myNode) 
	{
		this.myNode = myNode;
	}
}
