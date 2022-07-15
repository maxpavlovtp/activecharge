import React, { useEffect, useState } from "react";
import { Navigate } from "react-router-dom";
import { useAppDispatch, useAppSelector } from "../../hooks/reduxHooks";
import { getStationInfo } from "../../store/reducers/ActionCreators";
import Spinner from "../spinner/Spinner";

export default function PrivateRoute({ children }: { children: any }) {
  // const [loading, setLoading] = useState<boolean>(true);
  const { deviceStatus } = useAppSelector((state) => state.fetchReducer);
  const dispatch = useAppDispatch();
  let state: any = localStorage.getItem('stationState')

  // useEffect(() => {
  //   if (deviceStatus?.state !== null) {
  //     setLoading(false);
  //   }
  // }, [deviceStatus?.state]);
  console.log(deviceStatus); 
  return state === "IN_PROGRESS" ? (
    <Navigate to="/charging" />
  ) : (
    children
  );
}
