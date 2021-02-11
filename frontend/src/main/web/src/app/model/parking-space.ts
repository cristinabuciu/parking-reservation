export interface ParkingSpace {
    id: number;
    adminId: number;
    name: string;
    address: string;
    freeParkingSpaces: number;
    totalParkingSpaces: number;
    cost: number;
    costGranularity: string;
}
