public class EnemySpawnPointItem extends DungeonRoomPlanItem {
	// Distance from player when enemies spawn/attack
	private int m_AggroRadius;
	// The amount of enemies spawned
	private int m_EnemyCount;
	// Something to do with what type of enemy it is	
	// TODO put something here
	
	public EnemySpawnPointItem(int difficulty, Theme theme) {
		// TODO generate an AggroRadius
		// TODO generate an EnemyCount
		// TODO pick an enemy type etc
		;
	}

	public char toChar() {
		return 'E';
	}
}
