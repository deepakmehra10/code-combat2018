
import { User } from "./user";
import { CartItems } from "./cart-items";

/**Defines session model */
export class Session {
    user: User;
    cartItems: CartItems[];
  }