import React, { useEffect, useState } from "react";
import { Navigate } from "react-router-dom";
import { useAppDispatch, useAppSelector } from "../../hooks/reduxHooks";
import Spinner from "../spinner/Spinner";

export default function PrivateRoute({ children }: { children: any }) {
  const [loading, setLoading] = useState<boolean>(true);
  const { deviceStatus } = useAppSelector((state) => state.fetchReducer);
  const dispatch = useAppDispatch();

  // useEffect(() => {
  //   dispatch(getDeviceStatus());
  //   setLoading(false);
  // }, [dispatch]);

  if (loading) return <Spinner />;
  return deviceStatus?.state === "IN_PROGRESS" ? (
    <Navigate to="/charging" />
  ) : (
    children
  );
}
