
public class SceneNode {
	private String title;
	private String sceneDescription;
	private int sceneID = 1;
	private SceneNode left; // linked list
	private SceneNode middle;
	private SceneNode right;
	private SceneNode parent;
	private String letter; // if the Node is A,B or C
	private static int numScenes = 1;

	// Constructors
	public SceneNode(String title, String sceneDescription) {
		this.title = title;
		this.sceneDescription = sceneDescription;
		// this.sceneID = SceneNode.numScenes;
	}

	// getters and setters
	public static int getNumScenes() {
		return numScenes;
	}

	public static void setNumScenes(int numScenes) {
		SceneNode.numScenes = numScenes;
	}

	public String getLetter() {
		return letter;
	}

	public void setLetter(String letter) {
		this.letter = letter;
	}

	public SceneNode getLeft() {
		return left;
	}

	public void setLeft(SceneNode left) {
		this.left = left;
	}

	public SceneNode getMiddle() {
		return middle;
	}

	public void setMiddle(SceneNode middle) {
		this.middle = middle;
	}

	public SceneNode getRight() {
		return right;
	}

	public void setRight(SceneNode right) {
		this.right = right;
	}

	public int getSceneID() {
		return this.sceneID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSceneDescription() {
		return sceneDescription;
	}

	public void setSceneDescription(String sceneDescription) {
		this.sceneDescription = sceneDescription;
	}

	public SceneNode getParent() {
		return parent;
	}

	public void setParent(SceneNode parent) {
		this.parent = parent;
	}

	// Methods
	public void addSceneNode(SceneNode scene) throws FullSceneException {
		if (this.left == null) {
			this.left = scene;
			this.left.letter = "A";
			SceneNode.numScenes++;
			left.sceneID = SceneNode.numScenes;
		} else if (this.middle == null) {
			this.middle = scene;
			this.middle.letter = "B";
			SceneNode.numScenes++;
			middle.sceneID = SceneNode.numScenes;
		} else if (this.right == null) {
			this.right = scene;
			this.right.letter = "C";
			SceneNode.numScenes++;
			right.sceneID = SceneNode.numScenes;
		} else {
			throw new FullSceneException();

		}

	}

	public boolean isEnding() {
		if (this.left == null)
			return true;
		else
			return false;
	}

	public void displayScene() {
		System.out.println(this.title);// Prints the title
		System.out.println(this.sceneDescription);// Prints the Description of the scene
	}

	public void displayFullScene() {
		System.out.println("Scene ID #" + this.sceneID);
		System.out.println("Title: " + this.title);
		System.out.println("Scene: " + this.sceneDescription);

		if (this.left == null) {
			System.out.println("Leads to: ");
		} else if (this.middle == null) {
			System.out.println("Leads to: " + "'" + left.getTitle() + "' (#" + left.getSceneID() + ")");
		} else if (this.right == null) {
			System.out.println("Leads to: " + "'" + left.getTitle() + "'(#" + left.getSceneID() + ") ," + "'"
					+ middle.getTitle() + "' (#" + middle.getSceneID() + ")");
		} else {
			System.out.println("Leads to: " + "'" + left.getTitle() + "'(#" + left.getSceneID() + ") ," + "'"
					+ middle.getTitle() + "' (#" + middle.getSceneID() + ") ," + "'" + right.getTitle() + "' (#"
					+ right.getSceneID() + ")");
		}
	}

	public void removeScene(String option) {
		switch (option) {
		case "A":
			setLeft(getMiddle());
			left.setLetter("A");
			setMiddle(getRight());
			middle.setLetter("B");
			setRight(null);
			break;
		case "B":
			setMiddle(getRight());
			middle.setLetter("B");
			setRight(null);
			break;
		case "C":
			setRight(null);
			break;
		default:

		}
	}

	public String toString() {
		return this.title + " " + "(#" + this.sceneID + ")";
	}

}
