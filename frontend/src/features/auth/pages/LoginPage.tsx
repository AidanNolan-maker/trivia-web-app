import AuthCard from "../../../components/auth/AuthCard";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { Link, useNavigate } from "react-router-dom";

import { loginSchema, type LoginFormData } from "../schemas/loginSchema";

import { login } from "../api/authApi";
import useAuth from "../hooks/useAuth";

export default function LoginPage() {
    const navigate = useNavigate();

    const auth = useAuth();

    const {
        register,
        handleSubmit,
        formState: { errors, isSubmitting }
    } = useForm<LoginFormData>({
        resolver: zodResolver(loginSchema)
    });

    async function onSubmit(data: LoginFormData) {
        try {
            const response = await login(data);

            auth.login(
                response.token,
                response.username,
                response.role
            );

            navigate("/dashboard", {
                replace: true
            });
        } catch {
            alert("Invalid username or password.");
        }
    }

    return (
        <AuthCard title="Login">

            <form onSubmit={handleSubmit(onSubmit)}>

                <div className="mb-3">

                    <label className="form-label">Username</label>
                    <input className="form-control" {...register("username")} />

                    <div className="text-danger">{errors.username?.message}</div>

                </div>

                <div className="mb-3">

                    <label className="form-label">Password</label>
                    <input type="password" className="form-control" {...register("password")} />

                    <div className="text-danger">{errors.password?.message}</div>

                </div>

                <button className="btn btn-primary w-100" disabled={isSubmitting}>Login</button>

            </form>

            <hr />

            <p className="text-center mb-0">

                Don't have an account{" "}

                <Link to="/register">Create one</Link>

            </p>

        </AuthCard>
    );
}