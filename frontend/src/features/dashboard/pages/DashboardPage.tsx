import StatisticCard from "../../../components/ui/StatisticCard";
import StatisticSection from "../../../components/ui/StatisticSection";
import useAuth from "../../auth/hooks/useAuth";
import useUserStatistics from "../../statistics/hooks/useUserStatistics";

export default function DashboardPage() {
    const auth = useAuth();

    const {
        data,
        isPending,
        error,
    } = useUserStatistics();

    if (isPending) {
        return (
            <div className="text-center mt-5">

                <div className="spinner-border" role="status">

                    <span className="visually-hidden">Loading...</span>

                </div>

                <p className="mt-3">Loading your statistics...</p>

            </div>
        );
    }

    if (error || !data) {
        return (
            <div className="alert alert-danger" role="alert">Unable to load your statistics</div>
        );
    }


    return (
        <>

            <div className="mb-4">

                <h2>Welcome back, {auth.username}!</h2>

                <p className="text-muted">Here's an overview of your trivia performance.</p>

            </div>

            <StatisticSection title="Performance">

                <StatisticCard title="Games Played" value={data.gamesPlayed} />

                <StatisticCard title="Games Finished" value={data.gamesFinished} />

                <StatisticCard title="Games Won" value={data.gamesWon} />

                <StatisticCard title="Highest Score" value={data.highestScore} />

                <StatisticCard title="Average Score" value={data.averageScore.toFixed(1)} />

            </StatisticSection>

            <StatisticSection title="Accuracy">

                <StatisticCard title="Questions Answered" value={data.questionsAnswered} />

                <StatisticCard title="Correct Answers" value={data.correctAnswers} />

                <StatisticCard title="Overall Accuracy" value={`${data.accuracy.toFixed(1)}%`} />

                <StatisticCard title="Average Game Accuracy" value={`${data.averageAccuracyPerGame.toFixed(1)}%`} />

            </StatisticSection>

            <StatisticSection title="Win Streaks">

                <StatisticCard title="Current Win Streak" value={data.currentWinStreak} />

                <StatisticCard title="Longest Win Streak" value={data.longestWinStreak} />

            </StatisticSection>

            <StatisticSection title="Categories">

                <StatisticCard title="Favorite Category" value={data.favoriteCategory ?? "N/A"} />

                <StatisticCard title="Best Category" value={data.bestCategory ?? "N/A"} />

            </StatisticSection>

        </>
    );
}