import { User } from "./user";

export interface UserResponse {
    users: User[];
    count: number;
}