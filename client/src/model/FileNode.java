package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import online.TreeNode;

public class FileNode {
	private int id;
	private String name;
	private String size;
	private String type;
	private String url;
	private Set<FileNode> children = new HashSet<FileNode>();
	private FileNode parent;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

	public Set<FileNode> getChildren() {
		return children;
	}
	public void setChildren(Set<FileNode> children) {
		this.children = children;
	}

	public FileNode getParent() {
		return parent;
	}
	public void setParent(FileNode parent) {
		this.parent = parent;
	}
	
	
	public FileNode findTreeNodeById(int id) {
        if (this.getId() == id)
            return this;
        if (children.isEmpty() || children == null) {
            return null;
        } else {
            int childNumber = children.size();
            for (FileNode c : children) {
                FileNode child = c;
                FileNode resultNode = child.findTreeNodeById(id);
                if (resultNode != null) {
                    return resultNode;
                }
            }
            return null;
        }
    }
	//���û�к��ӽڵ㣬���ʼ�����Ӽ���
	public void initChildren() {
        if (children == null)
            children = new HashSet<FileNode>();
    }
	
	/* ����һ��child�ڵ㵽��ǰ�ڵ��� */
    public void addChildNode(FileNode fileNode) {
        initChildren();
        children.add(fileNode);
        fileNode.setParent(this);
    }
    /* ɾ���ڵ����������� */
    public void deleteNode() {
        FileNode parentNode = this.getParent();
        int id = this.id;
        if (parentNode != null) {
            parentNode.deleteChildNode(id);
        }
    }
    /* ɾ����ǰ�ڵ��ĳ���ӽڵ� */
    public void deleteChildNode(int childId) {
        Set<FileNode> childList = this.getChildren();
        int childNumber = childList.size();
        for (FileNode child : childList) {
            
            if (child.getId() == childId) {
                childList.remove(child);
                return;
            }
        }
    }
    
	public void printTree(FileNode f,int level){
		String preStr = "";
		for(int i=0;i<level;i++){
			preStr += "----";
		}
		System.out.println(preStr+f.getType());
		for(FileNode child : f.getChildren()){
			printTree(child,level+1);
		}
	}
}
