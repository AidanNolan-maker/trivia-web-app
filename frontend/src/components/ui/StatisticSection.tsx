import type { ReactNode } from "react";

interface StatisticSectionProps {
    title: string;
    children: ReactNode;
}

export default function StatisticSection({
    title,
    children,
}: StatisticSectionProps) {
    return (
        <section className="mb-5">

            <h3 className="mb-3">{title}</h3>

            <div className="row">{children}</div>

        </section>
    );
}