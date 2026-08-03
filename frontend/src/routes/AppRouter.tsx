import { createBrowserRouter, RouterProvider, Navigate } from "react-router-dom";

import LoginPage from "../features/auth/pages/LoginPage";
import RegisterPage from "../features/auth/pages/RegisterPage";
import DashboardPage from "../features/dashboard/pages/DashboardPage";

import ProtectedRoute from "./ProtectedRoute";
import MainLayout from "../layouts/MainLayout";
import PlayGamePage from "../features/game/pages/PlayGamePage";
import HistoryPage from "../features/history/pages/HistoryPage";

const router = createBrowserRouter([
    {
        path: "/login",
        element: <LoginPage />,
    },
    {
        path: "/register",
        element: <RegisterPage />
    },
    {
        element: <ProtectedRoute />,
        children: [
            {
                element: <MainLayout />,
                children: [
                    {
                        path: "/",
                        element: <Navigate to="/dashboard" replace />,
                    },
                    {
                        path: "/dashboard",
                        element: <DashboardPage />,
                    },
                    {
                        path: "/play",
                        element: <PlayGamePage />
                    },
                    {
                        path: "/history",
                        element: <HistoryPage />
                    },
                ],
            },
        ],

    },
]);

export default function AppRouter() {
    return <RouterProvider router={router} />
}