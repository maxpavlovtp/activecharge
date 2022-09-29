import { AppDispatch } from "../store";
import axios from "axios";
import { FetchSlice } from "./FetchSlice";

const urlV2Start = `${process.env.REACT_APP_LINK_SERVE}device/v2/start`;
const urlV2Status = `${process.env.REACT_APP_LINK_SERVE}device/v2/station/status?station_number=`;

export const idStart = (station: string) => async (dispatch: AppDispatch) => {
  const data = JSON.stringify({
    station_number: station,
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
    .catch(function (error: any) {
      dispatch(FetchSlice.actions.chargingDataFetchingError(error.message));
      console.log(error.message);
    })
    .then(function (response: any) {
      localStorage.setItem(
        "interval",
        response.data ? response.data.scan_interval_ms : 2000
      );
      console.log(JSON.stringify(response.data));
      dispatch(FetchSlice.actions.chargingDataFetchingSuccess());
    })
    .catch(function (error: any) {
      console.log(error);
      dispatch(FetchSlice.actions.chargingDataFetchingError(error.message));
    });
};

export const getStationInfo =
  (station: string) => async (dispatch: AppDispatch) => {
    try {
      dispatch(FetchSlice.actions.deviceStatusFetching());
      await axios
        .get(urlV2Status + station)
        .catch(function (error: any) {
          dispatch(FetchSlice.actions.deviceStatusFetchingError(error.message));
          console.log(error.message);
        })
        .then(function (result: any) {
          dispatch(FetchSlice.actions.deviceStatusFetchingSuccess(result.data));
          console.log(result.data);
        });
    } catch (e: any) {
      dispatch(FetchSlice.actions.deviceStatusFetchingError(e.message));
      console.log(e.message);
    }
  };
