public class FloorItem extends DungeonRoomPlanItem {
	private static FloorItem m_instance = new FloorItem();

	private FloorItem() {}

	public char toChar() {
		return '.';
	}

	public static FloorItem getInstance() {
		return m_instance;
	}
}
