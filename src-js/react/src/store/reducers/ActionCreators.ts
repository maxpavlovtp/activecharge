import { AppDispatch } from "../store";
import axios from "axios";
import { FetchSlice } from "./FetchSlice";

const urlV2Start = `${process.env.REACT_APP_LINK_SERVE}device/v2/start`;
const urlV2Status = `${process.env.REACT_APP_LINK_SERVE}device/v2/station/status?station_number=`;

//todo remove
const period_s = process.env.REACT_APP_PERIOD_S;

export const idStart = (station: any) => async (dispatch: AppDispatch) => {
  const data = JSON.stringify({
    station_number: station,
    period_s: period_s,
  });

  const config = {
    method: "post",
    url: urlV2Start,
    headers: {
      Accept: "application/json",
      "Content-Type": "application/json",
    },
    data: data,
  };
  dispatch(FetchSlice.actions.chargingDataFetching());
  await axios(config)
    .then(function (response: any) {
      localStorage.setItem(
        "interval",
        response.data ? response.data.scan_interval_ms : 2000
      );
      console.log(JSON.stringify(response.data));
      console.log(period_s);
      dispatch(FetchSlice.actions.chargingDataFetchingSuccess());
    })
    .catch(function (error: any) {
      console.log(error);
      dispatch(FetchSlice.actions.chargingDataFetchingError(error.message));
    });
};

export const getStationInfo =
  (station: any) => async (dispatch: AppDispatch) => {
    try {
      dispatch(FetchSlice.actions.deviceStatusFetching());
      axios.get(urlV2Status + station).then(function (result: any) {
        dispatch(FetchSlice.actions.deviceStatusFetchingSuccess(result.data));
        console.log(result.data);
      });
    } catch (e: any) {
      dispatch(FetchSlice.actions.deviceStatusFetchingError(e.message));
      console.log(e.message);
    }
  };
