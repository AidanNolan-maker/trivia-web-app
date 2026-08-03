import { useQuery } from "@tanstack/react-query";
import { getUserStatistics } from "../api/statisticsApi";

export default function useUserStatistics() {
    return useQuery({
        queryKey: ["userStatistics"],
        queryFn: getUserStatistics,
    });
}