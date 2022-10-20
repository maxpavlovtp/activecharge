import React, { useEffect, useState } from "react";
import { useSearchParams } from "react-router-dom";
import { useAppDispatch, useAppSelector } from "../../hooks/reduxHooks";
import { generatePaymentLink } from "../../store/reducers/ActionCreators";

export default function PayScreen() {
  const [getLink, setGetLink] = useState<any>(false);
  const dispatch = useAppDispatch();
  const { payLink } = useAppSelector((state) => state.fetchReducer);

  const condition = payLink !== "" ? true : false;

  const [searchParams] = useSearchParams();
  let stationNumber: any = searchParams.get("station");
  let hours: any = searchParams.get("hours");

  useEffect(() => {
    dispatch(generatePaymentLink(stationNumber, hours));
    if (payLink !== "") {
      window.open(payLink, "_blank");
    }
    console.log(payLink);
  }, [condition]);

  return <p>Wait...</p>;
}
