
import java.util.Arrays;
import java.util.Comparator;

public class EventScheduler {

    public static boolean canAttendAll(int[][] events) {
        if (events == null || events.length <= 1) {
            return true;
        }

        int[][] sorted = copyAndValidate(events);
        Arrays.sort(sorted, Comparator.comparingInt(a -> a[0]));

        for (int i = 1; i < sorted.length; i++) {
            if (sorted[i][0] < sorted[i - 1][1]) {
                return false;
            }
        }
        return true;
    }

    public static int minRoomsRequired(int[][] events) {
        if (events == null || events.length == 0) {
            return 0;
        }

        int[][] sorted = copyAndValidate(events);
        int n = sorted.length;
        int[] starts = new int[n];
        int[] ends = new int[n];

        for (int i = 0; i < n; i++) {
            starts[i] = sorted[i][0];
            ends[i] = sorted[i][1];
        }

        Arrays.sort(starts);
        Arrays.sort(ends);

        int start = 0, end = 0;
        int roomsInUse = 0, maxRooms = 0;

        while (start < n) {
            if (end < n && ends[end] <= starts[start]) {
                roomsInUse--;
                end++;
            } else {
                roomsInUse++;
                maxRooms = Math.max(maxRooms, roomsInUse);
                start++;
            }
        }
        return maxRooms;
    }

    private static int[][] copyAndValidate(int[][] events) {
        int[][] copy = new int[events.length][2];

        for (int i = 0; i < events.length; i++) {
            if (events[i] == null || events[i].length != 2) {
                throw new IllegalArgumentException(
                    "Each event needs a start and end time");
            }

            int start = events[i][0];
            int end = events[i][1];

            if (end < start) {
                throw new IllegalArgumentException(
                    "End time cannot be before start time");
            }

            copy[i] = new int[] {start, end};
        }
        return copy;
    }

    public static void main(String[] args) {
        int[][] events = {
            {9, 10}, {10, 11}, {11, 12}
        };

        System.out.println(canAttendAll(events));
        System.out.println(minRoomsRequired(events));
    }
}
