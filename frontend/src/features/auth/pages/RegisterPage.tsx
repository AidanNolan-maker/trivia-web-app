import { useState } from "react";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { Link, useNavigate } from "react-router-dom";

import AuthCard from "../../../components/auth/AuthCard";
import { register as registerUser } from "../api/authApi";
import {
    registerSchema,
    type RegisterFormData,
} from "../schemas/registerSchema";

export default function RegisterPage() {
    const navigate = useNavigate();

    const [error, setError] = useState<string | null>(null);
    const [success, setSuccess] = useState(false);

    const {
        register,
        handleSubmit,
        formState: { errors, isSubmitting },
    } = useForm<RegisterFormData>({
        resolver: zodResolver(registerSchema),
    });

    async function onSubmit(data: RegisterFormData) {
        setError(null);

        try {
            await registerUser({
                username: data.username,
                email: data.email,
                password: data.password,
            });

            setSuccess(true);

            setTimeout(() => {
                navigate("/login", {
                    replace: true,
                });
            }, 2000);
        } catch {
            setError("Unable to register. The username may already exist.");
        }
    }

    return (
        <AuthCard title="Create An Account">

            {success && (
                <div className="alert alert-success" role="alert">
                    Registration successful! Redirecting to the login page...
                </div>
            )}

            {error && (
                <div className="alert alert-danger" role="alert">
                    {error}
                </div>
            )}

            <form onSubmit={handleSubmit(onSubmit)}>

                <div className="mb-3">

                    <label className="form-label">Username</label>

                    <input className="form-control" {...register("username")} />

                    <div className="text-danger">{errors.username?.message}</div>

                </div>

                <div className="mb-3">

                    <label className="form-label">Email</label>

                    <input type="email" className="form-control" {...register("email")} />

                    <div className="text-danger">{errors.email?.message}</div>

                </div>

                <div className="mb-3">

                    <label className="form-label">Password</label>

                    <input type="password" className="form-control" {...register("password")} />

                    <div className="text-danger">{errors.password?.message}</div>

                </div>

                <div className="mb-4">

                    <label className="form-label">Confirm Password</label>

                    <input type="password" className="form-control" {...register("confirmPassword")} />

                    <div className="text-danger">{errors.confirmPassword?.message}</div>

                </div>

                <button type="submit" className="btn btn-primary w-100" disabled={isSubmitting || success}>
                    {isSubmitting ? "Creating Account..." : "Create Account"}
                </button>

            </form>

            <hr />

            <p className="text-center mb-0">

                Already have an account?{" "}

                <Link to="/login">Login</Link>

            </p>

        </AuthCard>
    );
}