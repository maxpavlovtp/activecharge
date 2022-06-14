export interface ITimer {
  seconds?: number;
  minutes?: number;
  hours?: number;
}

export interface IFetch {
  data?: any,
  loading?: boolean,
  error?: string,
}
