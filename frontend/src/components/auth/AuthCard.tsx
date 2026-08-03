import type { ReactNode } from "react";

interface AuthCardProps {
    title: string;
    children: ReactNode;
}

export default function AuthCard({
    title,
    children,
}: AuthCardProps) {
    return (
        <div className="container mt-5">

            <div className="row justify-content-center">

                <div className="col-md-6" col-lg-5>

                    <div className="card shadow">

                        <div className="card-body p-4">

                            <h2 className="text-center mb-4">{title}</h2>

                            {children}

                        </div>

                    </div>

                </div>

            </div>

        </div>
    );
}