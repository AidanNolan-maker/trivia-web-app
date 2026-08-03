interface StatisticCardProps {
    title: string;
    value: string | number;
}

export default function StatisticCard({
    title,
    value,
}: StatisticCardProps) {
    return (
        <div className="col-12 col-sm-6 col-xl-3 mb-4">

            <div className="card shadow-sm h-100">

                <div className="card-body text-center">

                    <h6 className="text-muted">{title}</h6>
                    <h3 className="mt-3">{value}</h3>

                </div>

            </div>

        </div>
    );
}