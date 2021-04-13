
public class SceneTree {
	private SceneNode root;
	private SceneNode cursor;

	public SceneTree(SceneNode root) {
		this.root = root;
		this.cursor = root;
	}

	public SceneNode getRoot() {
		return root;
	}

	public void setRoot(SceneNode root) {
		this.root = root;
	}

	public SceneNode getCursor() {
		return cursor;
	}

	public void setCursor(SceneNode cursor) {
		this.cursor = cursor;
	}

	public void moveCursorBackwards() throws NoSuchNodeException {
		if (cursor.getParent() == null)
			throw new NoSuchNodeException();
		cursor = cursor.getParent();
	}

	public void moveCursorForward(String option) throws NoSuchNodeException {
		switch (option) {
		case "A":
			if (cursor.getLeft() == null) {
				throw new NoSuchNodeException();
			}
			this.cursor = cursor.getLeft();
			break;
		case "B":
			if (cursor.getMiddle() == null) {
				throw new NoSuchNodeException();
			}
			this.cursor = cursor.getMiddle();
			break;
		case "C":
			if (cursor.getRight() == null) {
				throw new NoSuchNodeException();
			}
			this.cursor = cursor.getRight();
			break;
		default:
			throw new NoSuchNodeException();

		}
	}

	public void addNewNode(String title, String sceneDescription) throws FullSceneException {
		SceneNode temp = new SceneNode(title, sceneDescription);
		try {
			cursor.addSceneNode(temp);
		} catch (FullSceneException ex) {
			throw new FullSceneException();
		}
	}

	public void removeScene(String option) throws NoSuchNodeException {
		switch (option) {
		case "A":
			if (cursor.getLeft() == null) {
				throw new NoSuchNodeException();
			}
			cursor.setLeft(cursor.getMiddle());
			//cursor.getLeft().setLetter("A");    //Create IF(null) statements.   Create a helper method that "resets" all the letterings 
			cursor.setMiddle(cursor.getRight());
			//cursor.getMiddle().setLetter("B");
			cursor.setRight(null);
			break;
		case "B":
			if (cursor.getMiddle() == null) {
				throw new NoSuchNodeException();
			}
			cursor.setMiddle(cursor.getRight());
			cursor.getMiddle().setLetter("B");
			cursor.setRight(null);
			break;
		case "C":
			if (cursor.getRight() == null) {
				throw new NoSuchNodeException();
			}
			cursor.setRight(null);
			break;
		default:
			throw new NoSuchNodeException();

		}
	}

	public void moveScene(int sceneIDToMoveTo) throws NoSuchNodeException, FullSceneException {
		if (sceneIDToMoveTo > SceneNode.getNumScenes()) {
			throw new NoSuchNodeException();
		}
		try {
			findSceneID(this.root, sceneIDToMoveTo);

		} catch (NoSuchNodeException ex) {
			throw new NoSuchNodeException();
		} catch (FullSceneException ex1) {
			throw new FullSceneException();
		}

	}

	public void findSceneID(SceneNode node, int sceneID) throws FullSceneException, NoSuchNodeException {
		if (node == null) {
			return;
		}
		if (node.getSceneID() == sceneID) {
			if (node.getLeft() == null) {
				node.setLeft(this.cursor);
				System.out.println("Successful1");
			} else if (node.getMiddle() == null) {
				node.setMiddle(this.cursor);
				System.out.println("Successful2");
			} else if (node.getRight() == null) {
				node.setRight(this.cursor);
				System.out.println("Successful3");
			} else {
				throw new FullSceneException();
			}
		}

		while (!node.isEnding()) {
			findSceneID(node.getLeft(), sceneID);
			findSceneID(node.getMiddle(), sceneID);
			findSceneID(node.getRight(), sceneID);
			break;
		}

		// throw new NoSuchNodeException();

	}

	public String getPathFromRoot() {
		String path = cursor.getTitle();
		SceneNode sub = cursor;
		while (sub.getParent() != null) {
			path = sub.getParent().getTitle() + ", " + path;
			sub = sub.getParent();
		}
		return path;
	}

	public String toString() {
		SceneNode tempRoot = this.root;
		String sceneTree = helperToString(tempRoot);
		return sceneTree;
	}

	public String helperToString(SceneNode node) {
		String temp = "";
		if (node == null) {
			return "";
		}

		if (node.getSceneID() == 1) {
			temp = temp + (node + "*" + "\n");
		} else {
			temp = temp + (node.getLetter() + ")" + node + "\n");
		}

		while (!node.isEnding()) {
			helperToString(node.getLeft());
			helperToString(node.getMiddle());
			helperToString(node.getRight());
			break;
		}

		return temp;
	}

}
