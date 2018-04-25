public class  sFirstFailingVersion {

    public static long firstBadVersion(long n, IsFailingVersion isBadVersion) {
        long start = 0;
        while (n-start>1) {
            long mid = (n+start) / 2;
            if (isBadVersion.isFailingVersion(mid)) {
                n = mid;
            } else {
                start = mid;
            }
        }
        return n;
    }
}
