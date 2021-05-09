import { AmountType } from './amount-type.enum';
export class Ingredient {
    id: number;
    componentId: number;
    amount: number;
    amountType: AmountType;
    componentName: string;
}
