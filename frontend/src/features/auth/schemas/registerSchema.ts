import { z } from "zod";

export const registerSchema = z.object({
    username: z
        .string()
        .trim()
        .min(3, "Username must be at least 3 characters.")
        .max(50, "Username cannot exceed 50 characters."),

    password: z
        .string()
        .min(8, "Password must be at least 8 characters."),

    confirmPassword: z
        .string()
})
.refine(
    (data) => data.password === data.confirmPassword,
    {
        message: "Passwords do not match.",
        path: ["confirmPassword"]
    }
);

export type RegisterFormData = z.infer<typeof registerSchema>;