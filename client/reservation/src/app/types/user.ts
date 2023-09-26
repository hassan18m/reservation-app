import { Product } from "./product";

export interface User {
    id: Number;
    firstName: string;
    lastName: string;
    password: string;
    email: string;
    products: Product[];
}

export interface UserData {
    id: Number;
    firstName: string;
    lastName: string;
    email: string;
}