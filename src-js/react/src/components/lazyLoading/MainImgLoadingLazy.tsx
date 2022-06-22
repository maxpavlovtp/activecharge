import React, { useEffect, useState } from "react";
import styles from "./MainImgLoadingLazy.module.css";
export default function MainImgLoadingLazy({
  placeholderSrc,
  src,
  alt,
  width,
  heigth,
}: {
  placeholderSrc: any;
  src: any;
  alt: any;
  width: any;
  heigth: any;
}) {
  const [imgSrc, setImgSrc] = useState(placeholderSrc || src);
  useEffect(() => {
    const img = new Image();
    img.src = src;
    img.onload = () => {
      setImgSrc(src);
    };
  }, [src]);

  return (
    <img
      {...{ src: imgSrc }}
      alt={alt || ""}
      className={
        placeholderSrc && imgSrc === placeholderSrc
          ? styles.loading
          : styles.loaded
      }
      width={width}
      height={heigth}
    />
  );
}
