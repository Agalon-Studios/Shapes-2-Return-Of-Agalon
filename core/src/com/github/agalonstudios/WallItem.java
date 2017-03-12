public class WallItem extends DungeonRoomPlanItem {
	private static WallItem m_instance = new WallItem();

	private WallItem() {}

	public char toChar() {
		return 'X';
	}

	public static WallItem getInstance() {
		return m_instance;
	}
}
