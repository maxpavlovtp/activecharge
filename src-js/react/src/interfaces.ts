export interface ITimer {
  seconds?: number;
  minutes?: number;
  hours?: number;
  fontSize?: string;
  margin?: string;
}

export interface IFetch {
  data?: any,
  loading?: boolean,
  error?: string,
}
