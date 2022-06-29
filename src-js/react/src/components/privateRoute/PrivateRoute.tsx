import React, { useEffect, useState } from "react";
import { Navigate } from "react-router-dom";
import { useAppSelector } from "../../hooks/reduxHooks";

export default function PrivateRoute({ children }: { children: any }) {
  const { deviceStatus } = useAppSelector((state) => state.fetchReducer);

  return deviceStatus?.data?.switchState === false ? (
    children
  ) : (
    <Navigate to="/charging" />
  );
}
