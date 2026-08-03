import { createContext } from "react";

export interface AuthContextType {
    isAuthenticated: boolean;

    username: string | null;

    role: string | null;

    login: (
        token: string,
        username: string,
        role: string
    ) => void;

    logout: () => void;
}

export const AuthContext =
    createContext<AuthContextType | null>(null);