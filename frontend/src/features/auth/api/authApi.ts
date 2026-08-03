import api from "../../../api/axios";
import type { LoginRequest } from "../types/LoginRequest";
import type { LoginResponse } from "../types/LoginResponse";
import type { RegisterRequest } from "../types/RegisterRequest";
import type { RegisterResponse } from "../types/RegisterResponse";

export async function login(
    request: LoginRequest
): Promise<LoginResponse> {
    const response = await api.post<LoginResponse>(
        "/auth/login",
        request
    );

    return response.data;
}

export async function register(
    request: RegisterRequest
): Promise<RegisterResponse> {
    const response = await api.post<RegisterResponse>(
        "/auth/register",
        request
    );

    return response.data;
}