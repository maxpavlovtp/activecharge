export interface ITimer {
  seconds?: number;
  minutes?: number;
  hours?: number;
}

export interface IFetch {
  data: object;
  loading: boolean;
  error?: string;
}
