import { NavLink, useNavigate } from "react-router-dom";
import useAuth from "../../features/auth/hooks/useAuth";

export default function Navbar() {
    const auth = useAuth();

    const navigate = useNavigate();

    function handleLogout() {
        auth.logout();

        navigate("/login", { replace: true });
    }

    return (
        <nav className="navbar navbar-expand-lg navbar-dark bg-dark">

            <div className="container">

                <NavLink className="navbar-brand" to="/dashboard">Trivia Web App</NavLink>

                <button
                    className="navbar-toggler"
                    type="button"
                    data-bs-toggle="collapse"
                    data-bs-target="#navbarNav">
                        <span className="navbar-toggler-icon"></span>
                    </button>

                    <div className="collapse navbar-collapse" id="navbarNav">

                        <ul className="navbar-nav me-auto">

                            <li className="nav-item">

                                <NavLink to="/dashboard" className={({ isActive }) =>
                                isActive ? "nav-link active" : "nav-link"
                            }>Dashboard</NavLink>

                            </li>

                            <li className="nav-item">

                                <NavLink to="/play" className={({ isActive }) =>
                                isActive ? "nav-link active" : "nav-link"
                                }>Play Game</NavLink>

                            </li>

                            <li className="nav-item">

                                <NavLink to="/history" className={({ isActive }) =>
                                isActive ? "nav-link active" : "nav-link"
                                }>History</NavLink>

                            </li>

                        </ul>

                        <span className="navbar-text me-3">Welcome, {auth.username}</span>

                        <button
                            className="btn btn-outline-light" onClick={handleLogout}>Logout</button>

                    </div>

            </div>

        </nav>
    );
}