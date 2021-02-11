export interface Transaction {
    id: number;
    userId: number;
    parkingId: number;
    duration: number;
    arrivalTime: Date;
    departureTime: Date;
    cost: number;
}