package micronet.model;

public class FactionValues {
	public static class Attitude {
		public static final byte Neutral = 0;
		public static final byte Confederate = 1;
		public static final byte Rebel = 2;
		public static final byte Outlaw = 3;
	}

	public static class AttitudeThresholds {
		public static final float[] rankPercentage = { 0.3f, 0.6f, 0.95f };
		public static final float hostilePercentage = -0.2f;
		public static final float hatefulPercentage = -0.8f;
	}

	private float confederateReputation = 0;
	private float rebelReputation = 0;

	public FactionValues() {
	}

	public FactionValues(int initialAttitude) {
		if (initialAttitude == Attitude.Confederate) {
			confederateReputation = 0.4f;
			rebelReputation = -0.3f;
		}
		if (initialAttitude == Attitude.Rebel) {
			rebelReputation = 0.4f;
			confederateReputation = -0.3f;
		}
	}

	public float getConfederateReputation() {
		return confederateReputation;
	}

	public float getRebelReputation() {
		return rebelReputation;
	}

	public int getAttitude() {
		if (confederateReputation < AttitudeThresholds.hatefulPercentage
				&& rebelReputation < AttitudeThresholds.hatefulPercentage)
			return Attitude.Outlaw;
		if (confederateReputation > AttitudeThresholds.rankPercentage[0])
			return Attitude.Confederate;
		if (rebelReputation > AttitudeThresholds.rankPercentage[0])
			return Attitude.Rebel;
		return Attitude.Neutral;
	}

	public void addReputation(int attitude, float value) {
		if (attitude == Attitude.Confederate) {
			confederateReputation += value;
		}
		if (attitude == Attitude.Rebel) {
			rebelReputation += value;
		}

		if (confederateReputation < -1)
			confederateReputation = -1;
		if (confederateReputation > 1)
			confederateReputation = 1;
		if (rebelReputation < -1)
			rebelReputation = -1;
		if (rebelReputation > 1)
			rebelReputation = 1;
	}
	
    public int getRank()
    {
        if (getAttitude() == Attitude.Confederate)
        {
            if (confederateReputation > AttitudeThresholds.rankPercentage[2])
                return 3;
            if (confederateReputation > AttitudeThresholds.rankPercentage[1])
                return 2;
            if (confederateReputation > AttitudeThresholds.rankPercentage[0])
                return 1;
        }
        if (getAttitude() == Attitude.Rebel)
        {
            if (rebelReputation > AttitudeThresholds.rankPercentage[2])
                return 3;
            if (rebelReputation > AttitudeThresholds.rankPercentage[1])
                return 2;
            if (rebelReputation > AttitudeThresholds.rankPercentage[0])
                return 1;
        }
        return 0;
    }
    
    public String getName()
    {
        if (getAttitude() == Attitude.Confederate)
            return "Confederate";
        if (getAttitude() == Attitude.Rebel)
            return "Rebel";
        if (getAttitude() == Attitude.Outlaw)
            return "Outlaw";
        return "Neutral";
    }

    public boolean isHostile(String faction)
    {
        switch (faction)
        {
            case "Outlaw":
                if (getAttitude() == Attitude.Outlaw)
                    return false;
                return true;
            case "Confederate":
                if (getAttitude() == Attitude.Outlaw)
                    return true;
                if (getAttitude() == Attitude.Rebel)
                    return true;
                return false;
            case "Rebel":
                if (getAttitude() == Attitude.Outlaw)
                    return true;
                if (getAttitude() == Attitude.Confederate)
                    return true;
                return false;
            case "Neutral":
                if (getAttitude() == Attitude.Outlaw)
                    return true;
                return false;
            default:
                return false;
        }
    }
}
