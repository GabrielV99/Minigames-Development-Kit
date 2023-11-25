package ro.Gabriel.Misc;

import org.bukkit.Bukkit;

public class ServerVersion {

    public static Version getVersion() {
        return Version.getCurrent();
    }

    public static void load() {
        Version.load();
    }

    public enum Version {

        v0_0_R0,
        v1_8_R1, v1_8_R2, v1_8_R3,
        v1_9_R1, v1_9_R2,
        v1_10_R1, v1_10_R2,
        v1_11_R1,
        v1_12_R1,
        v1_13_R1, v1_13_R2,
        v1_14_R1, v1_14_R2,
        v1_15_R1, v1_15_R2,
        v1_16_R1, v1_16_R2, v1_16_R3,
        v1_17_R1, v1_17_R2,
        v1_18_R1, v1_18_R2;

    	/*	--rev 1.8 = 1_8_R1
			--rev 1.8.3 = 1_8_R2
			--rev 1.8.8 = 1_8_R3
			--rev 1.9.2 = 1_9_R1
			--rev 1.9.4 = 1_9_R2
			--rev 1.10.2 = 1_10_R1
			--rev 1.11.2 = 1_11_R1
			--rev 1.12.2 = 1_12_R1
			--rev 1.13 = 1_13_R1
			--rev 1.13.2 = 1_13_R2
			--rev 1.14.4 = 1_14_R1
			--rev 1.15.2 = 1_15_R1
			--rev 1.16.1 = 1_16_R1
			--rev 1.16.3 = 1_16_R2
			--rev 1.16.4 = 1_16_R3*/

        private final Integer value;
        private final String shortVersion;
        private static String[] packageVersion;
        private static Version current;

        private Version() {
            this.value = Integer.valueOf(this.name().replaceAll("[^\\d.]", ""));
            this.shortVersion = this.name().substring(0, this.name().length() - 3);
        }

        public Integer getValue() {
            return this.value;
        }

        public String getShortVersion() {
            return this.shortVersion;
        }

        public static String[] getPackageVersion() {
            return Version.packageVersion;
        }

        public static Version getCurrent() {
            return Version.current;
        }

        public boolean isLower(final Version version) {
            return this.getValue() < version.getValue();
        }

        public boolean isHigher(final Version version) {
            return this.getValue() > version.getValue();
        }

        public boolean isEqual(final Version version) {
            return this.getValue().equals(version.getValue());
        }

        public boolean isEqualOrLower(final Version version) {
            return this.getValue() <= version.getValue();
        }

        public boolean isEqualOrHigher(final Version version) {
            return this.getValue() >= version.getValue();
        }

        public static boolean isCurrentEqualOrHigher(final Version v) {
            return getCurrent().getValue() >= v.getValue();
        }

        public static boolean isCurrentHigher(final Version v) {
            return getCurrent().getValue() > v.getValue();
        }

        public static boolean isCurrentLower(final Version v) {
            return getCurrent().getValue() < v.getValue();
        }

        public static boolean isCurrentEqualOrLower(final Version v) {
            return getCurrent().getValue() <= v.getValue();
        }

        public static boolean isCurrentEqual(final Version v) {
            return getCurrent().getValue().equals(v.getValue());
        }

        private static void load() {
            if (Version.packageVersion == null) {
                Version.packageVersion = Bukkit.getServer().getClass().getPackage().getName().split("\\.");
            }

            final String[] v = getPackageVersion();
            final String vv = v[v.length - 1];
            for (final Version one : values()) {
                if (one.name().equalsIgnoreCase(vv)) {
                    Version.current = one;
                    break;
                }
            }
            if (Version.current == null) {
                Version.current = Version.v0_0_R0;
            }
        }
    }
}